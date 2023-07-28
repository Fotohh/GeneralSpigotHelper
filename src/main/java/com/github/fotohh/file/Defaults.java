package com.github.fotohh.file;

/**
 * Recommended to create an enum implementing this class, and you can add it to the {@link YMLFile} class
 * where you can add different defaults into the file. They will be set upon creation or
 * created if the path returns null.
 */
public interface Defaults {

    /**
     * This gets the path that was set in your enum class.
     *
     * @return the path in the {@link YMLFile}
     */
    String getPath();

    /**
     * Gets the default value from the object that was set in your enum class.
     *
     * @return The default value that will be set in the corresponding path {@link Defaults#getPath()}
     */
    Object getDefaultValue();

    /**
     * Typically, this would be filled in with Enum.getValues();
     * which returns an array of the Enums set.
     *
     * @return Returns a list of the enum values containing {@link Defaults#getPath()} and {@link Defaults#getDefaultValue()}.
     */
    Defaults[] getValues();

    /**
     * Create an instance of the Defaults class to then be used inside {@link YMLFile}
     *
     * @return the instance of {@link Defaults}
     */
    Defaults instance();

    /**
     * Gets the instance of the enum without the need to create an enum entry.
     *
     * @param <T> The type of the enum extending Defaults.
     * @param enumClass The class of the enum extending Defaults.
     * @return The instance of the enum.
     */
    static <T extends Defaults> T getEnumInstance(Class<T> enumClass) {
        Defaults[] values = enumClass.getEnumConstants();
        if (values == null || values.length == 0) {
            throw new IllegalArgumentException("The provided class does not represent an enum or does not have any enum constants.");
        }
        return enumClass.cast(values[0].instance());
    }
}
