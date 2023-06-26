package com.alpineclient.plugin.api.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.NotImplementedException;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an image texture.
 *
 * @author BestBearr
 * @since 1.0
 */
@Getter @AllArgsConstructor
public final class ClientResource {
    private final Type type;
    private final String value;

    /**
     * Compiles an internal resource.
     * @see Type#INTERNAL
     *
     * @param value The local path to the resource
     * @return The compiled resource
     */
    @NotNull
    public static ClientResource internal(@NotNull String value) {
        return new ClientResource(Type.INTERNAL, value);
    }

    /**
     * Compiles an external resource.
     * @see Type#EXTERNAL
     *
     * @param value The URL to the resource
     * @return The compiled resource
     */
    @NotNull
    public static ClientResource external(@NotNull String value) {
        throw new NotImplementedException();
        // return new ClientResource(Type.EXTERNAL, value);
    }

    /**
     * Represents where the client looks for a resource.
     */
    public enum Type {
        /**
         * The resource is located in the client's inbuilt resources.
         */
        INTERNAL,
        /**
         * The resource is located online.
         */
        EXTERNAL
    }
}