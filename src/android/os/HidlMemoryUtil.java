package android.os;

import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.util.Log;
import com.android.internal.util.Preconditions;
import java.io.FileDescriptor;
import java.nio.ByteBuffer;
import java.nio.DirectByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes3.dex */
public final class HidlMemoryUtil {
    private static final String TAG = "HidlMemoryUtil";

    private HidlMemoryUtil() {
    }

    public static HidlMemory byteArrayToHidlMemory(byte[] bArr) {
        return byteArrayToHidlMemory(bArr, null);
    }

    public static HidlMemory byteArrayToHidlMemory(byte[] bArr, String str) {
        Preconditions.checkNotNull(bArr);
        if (bArr.length == 0) {
            return new HidlMemory("ashmem", 0L, null);
        }
        if (str == null) {
            str = "";
        }
        try {
            SharedMemory sharedMemoryCreate = SharedMemory.create(str, bArr.length);
            try {
                ByteBuffer byteBufferMapReadWrite = sharedMemoryCreate.mapReadWrite();
                byteBufferMapReadWrite.put(bArr);
                SharedMemory.unmap(byteBufferMapReadWrite);
                HidlMemory hidlMemorySharedMemoryToHidlMemory = sharedMemoryToHidlMemory(sharedMemoryCreate);
                if (sharedMemoryCreate != null) {
                    sharedMemoryCreate.close();
                }
                return hidlMemorySharedMemoryToHidlMemory;
            } finally {
            }
        } catch (ErrnoException e) {
            throw new RuntimeException(e);
        }
    }

    public static HidlMemory byteListToHidlMemory(List<Byte> list) {
        return byteListToHidlMemory(list, null);
    }

    public static HidlMemory byteListToHidlMemory(List<Byte> list, String str) {
        Preconditions.checkNotNull(list);
        if (list.isEmpty()) {
            return new HidlMemory("ashmem", 0L, null);
        }
        if (str == null) {
            str = "";
        }
        try {
            SharedMemory sharedMemoryCreate = SharedMemory.create(str, list.size());
            try {
                ByteBuffer byteBufferMapReadWrite = sharedMemoryCreate.mapReadWrite();
                Iterator<Byte> it = list.iterator();
                while (it.hasNext()) {
                    byteBufferMapReadWrite.put(it.next().byteValue());
                }
                SharedMemory.unmap(byteBufferMapReadWrite);
                HidlMemory hidlMemorySharedMemoryToHidlMemory = sharedMemoryToHidlMemory(sharedMemoryCreate);
                if (sharedMemoryCreate != null) {
                    sharedMemoryCreate.close();
                }
                return hidlMemorySharedMemoryToHidlMemory;
            } finally {
            }
        } catch (ErrnoException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] hidlMemoryToByteArray(HidlMemory hidlMemory) throws ErrnoException {
        Preconditions.checkNotNull(hidlMemory);
        Preconditions.checkArgumentInRange(hidlMemory.getSize(), 0L, 2147483647L, "Memory size");
        Preconditions.checkArgument(hidlMemory.getSize() == 0 || hidlMemory.getName().equals("ashmem"), "Unsupported memory type: %s", hidlMemory.getName());
        if (hidlMemory.getSize() == 0) {
            return new byte[0];
        }
        ByteBuffer buffer = getBuffer(hidlMemory);
        byte[] bArr = new byte[buffer.remaining()];
        buffer.get(bArr);
        return bArr;
    }

    public static ArrayList<Byte> hidlMemoryToByteList(HidlMemory hidlMemory) throws ErrnoException {
        Preconditions.checkNotNull(hidlMemory);
        Preconditions.checkArgumentInRange(hidlMemory.getSize(), 0L, 2147483647L, "Memory size");
        Preconditions.checkArgument(hidlMemory.getSize() == 0 || hidlMemory.getName().equals("ashmem"), "Unsupported memory type: %s", hidlMemory.getName());
        if (hidlMemory.getSize() == 0) {
            return new ArrayList<>();
        }
        ByteBuffer buffer = getBuffer(hidlMemory);
        ArrayList<Byte> arrayList = new ArrayList<>(buffer.remaining());
        while (buffer.hasRemaining()) {
            arrayList.add(Byte.valueOf(buffer.get()));
        }
        return arrayList;
    }

    public static HidlMemory sharedMemoryToHidlMemory(SharedMemory sharedMemory) {
        if (sharedMemory == null) {
            return new HidlMemory("ashmem", 0L, null);
        }
        return fileDescriptorToHidlMemory(sharedMemory.getFileDescriptor(), sharedMemory.getSize());
    }

    public static HidlMemory fileDescriptorToHidlMemory(FileDescriptor fileDescriptor, int i) {
        Preconditions.checkArgument(fileDescriptor != null || i == 0);
        if (fileDescriptor == null) {
            return new HidlMemory("ashmem", 0L, null);
        }
        try {
            return new HidlMemory("ashmem", i, new NativeHandle(Os.dup(fileDescriptor), true));
        } catch (ErrnoException e) {
            throw new RuntimeException(e);
        }
    }

    private static ByteBuffer getBuffer(HidlMemory hidlMemory) throws ErrnoException {
        try {
            final int size = (int) hidlMemory.getSize();
            if (size == 0) {
                return ByteBuffer.wrap(new byte[0]);
            }
            NativeHandle handle = hidlMemory.getHandle();
            final long jMmap = Os.mmap(0L, size, OsConstants.PROT_READ, OsConstants.MAP_SHARED, handle.getFileDescriptor(), 0L);
            return new DirectByteBuffer(size, jMmap, handle.getFileDescriptor(), new Runnable() { // from class: android.os.HidlMemoryUtil$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() throws ErrnoException {
                    HidlMemoryUtil.lambda$getBuffer$0(jMmap, size);
                }
            }, true);
        } catch (ErrnoException e) {
            throw new RuntimeException(e);
        }
    }

    static /* synthetic */ void lambda$getBuffer$0(long j, int i) throws ErrnoException {
        try {
            Os.munmap(j, i);
        } catch (ErrnoException e) {
            Log.wtf(TAG, e);
        }
    }
}
