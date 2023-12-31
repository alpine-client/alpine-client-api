package com.alpineclient.plugin.util;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import io.netty.buffer.ByteBuf;

/**
 * @author Thomas Wearmouth
 * Created on 21/06/2023
 */
public final class ByteBufUtils {
    /**
     * The number of bytes to write the supplied int using the 7 bit varint encoding.
     *
     * @param toCount The number to analyse
     * @return The number of bytes it will take to write it (maximum of 5)
     */
    public static int varIntByteCount(int toCount) {
        return (toCount & 0xFFFFFF80) == 0 ? 1
                : ((toCount & 0xFFFFC000) == 0 ? 2
                : ((toCount & 0xFFE00000) == 0 ? 3
                : ((toCount & 0xF0000000) == 0 ? 4 : 5)));
    }

    /**
     * Read a varint from the supplied buffer.
     *
     * @param buf The buffer to read from
     * @param maxSize The maximum length of bytes to read
     * @return The integer
     */
    public static int readVarInt(ByteBuf buf, int maxSize) {
        Preconditions.checkArgument(maxSize < 6 && maxSize > 0, "Varint length is between 1 and 5, not %d", maxSize);
        int i = 0;
        int j = 0;
        byte b0;

        do {
            b0 = buf.readByte();
            i |= (b0 & 127) << j++ * 7;

            if (j > maxSize) {
                throw new RuntimeException("VarInt too big");
            }
        }
        while ((b0 & 128) == 128);

        return i;
    }

    /**
     * Write an integer to the buffer using variable length encoding. The maxSize constrains
     * how many bytes (and therefore the maximum number) that will be written.
     *
     * @param to The buffer to write to
     * @param toWrite The integer to write
     * @param maxSize The maximum number of bytes to use
     */
    public static void writeVarInt(ByteBuf to, int toWrite, int maxSize) {
        Preconditions.checkArgument(varIntByteCount(toWrite) <= maxSize, "Integer is too big for %d bytes", maxSize);
        while ((toWrite & -128) != 0) {
            to.writeByte(toWrite & 127 | 128);
            toWrite >>>= 7;
        }

        to.writeByte(toWrite);
    }

    /**
     * Read a UTF8 string from the byte buffer.
     *
     * @param from The buffer to read from
     * @return The string
     */
    public static String readUTF8String(ByteBuf from) {
        int len = readVarInt(from, 2);
        String str = from.toString(from.readerIndex(), len, Charsets.UTF_8);
        from.readerIndex(from.readerIndex() + len);
        return str;
    }

    /**
     * Write a String with UTF8 byte encoding to the buffer.
     *
     * @param to the buffer to write to
     * @param string The string to write
     */
    public static void writeUTF8String(ByteBuf to, String string) {
        byte[] utf8Bytes = string.getBytes(Charsets.UTF_8);
        Preconditions.checkArgument(varIntByteCount(utf8Bytes.length) < 3, "The string is too long for this encoding.");
        writeVarInt(to, utf8Bytes.length, 2);
        to.writeBytes(utf8Bytes);
    }
}

